package com.nttdata.infraestructure.repository;

import com.nttdata.domain.contract.ExchangeRepository;
import com.nttdata.domain.models.ExchangeDto;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.quarkus.mongodb.reactive.ReactiveMongoDatabase;
import io.smallrye.mutiny.Uni;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.enterprise.context.ApplicationScoped;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

@ApplicationScoped
public class ExchangeRepositoryImpl implements ExchangeRepository {
  private final ReactiveMongoClient reactiveMongoClient;

  public ExchangeRepositoryImpl(ReactiveMongoClient reactiveMongoClient) {
    this.reactiveMongoClient = reactiveMongoClient;
  }

  @Override
  public Uni<ExchangeDto> addPetition(ExchangeDto exchangeDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("changes");
    ReactiveMongoCollection<Document> collection = database.getCollection("change");
    String numberTransaction = UUID.randomUUID().toString();
    Document document = new Document()
        .append("typeDocument", exchangeDto.getTypeDocument())
        .append("numberDocument", exchangeDto.getNumberDocument())
        .append("numberTelephone", exchangeDto.getNumberTelephone())
        .append("typePay", exchangeDto.getTypePay())
        .append("amount", exchangeDto.getAmount())
        .append("numberTransaction", numberTransaction)
        .append("created_datetime", this.getDateNow())
        .append("updated_datetime", this.getDateNow())
        .append("active", "S");
    exchangeDto.setNumberTransaction(numberTransaction);

    return collection.insertOne(document).replaceWith(exchangeDto);
  }

  @Override
  public Uni<ExchangeDto> findByNumberTransaction(ExchangeDto exchangeDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("changes");
    ReactiveMongoCollection<Document> collection = database.getCollection("change");
    return collection
        .find(new Document("numberTransaction", exchangeDto.getNumberTransaction())).map(doc->{
          ExchangeDto exchange = new ExchangeDto();
          exchange.setNumberDocument(doc.getLong("numberDocument"));
          exchange.setTypeDocument(doc.getInteger("typeDocument"));
          exchange.setTypePay(doc.getInteger("typePay"));
          exchange.setNumberTransaction(doc.getString("numberTransaction"));
          exchange.setNumberTelephone(doc.getString("numberTelephone"));
          exchange.setAmount(doc.getDouble("amount"));
          exchange.setCreated_datetime(doc.getString("created_datetime"));
          exchange.setUpdated_datetime(doc.getString("updated_datetime"));
          exchange.setActive(doc.getString("active"));
          return exchange;
        }).filter(s->s.getActive().equals("S")).toUni();
  }

  private static String getDateNow(){
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(date).toString();
  }
}
