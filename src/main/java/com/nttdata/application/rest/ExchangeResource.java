package com.nttdata.application.rest;

import com.nttdata.btask.interfaces.ExchangeService;
import com.nttdata.domain.models.ExchangeDto;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/exchanges")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExchangeResource {
  private final ExchangeService exchangeService;

  public ExchangeResource(ExchangeService exchangeService) {
    this.exchangeService = exchangeService;
  }

  @POST
  public Uni<ExchangeDto> registerPetition(ExchangeDto exchangeDto){
    return exchangeService.addPetition(exchangeDto);
  }

  @POST
  @Path("/search")
  public Uni<ExchangeDto> findByNumberTransaction(ExchangeDto exchangeDto){
    return exchangeService.findByNumberTransaction(exchangeDto);
  }
}
