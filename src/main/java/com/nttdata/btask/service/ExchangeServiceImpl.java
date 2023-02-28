package com.nttdata.btask.service;

import com.nttdata.btask.interfaces.ExchangeService;
import com.nttdata.domain.contract.ExchangeRepository;
import com.nttdata.domain.models.ExchangeDto;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExchangeServiceImpl implements ExchangeService {
  private final ExchangeRepository exchangeRepository;

  public ExchangeServiceImpl(ExchangeRepository exchangeRepository) {
    this.exchangeRepository = exchangeRepository;
  }

  @Override
  public Uni<ExchangeDto> addPetition(ExchangeDto exchangeDto) {
    return exchangeRepository.addPetition(exchangeDto);
  }

  @Override
  public Uni<ExchangeDto> findByNumberTransaction(ExchangeDto exchangeDto) {
    return exchangeRepository.findByNumberTransaction(exchangeDto);
  }
}
