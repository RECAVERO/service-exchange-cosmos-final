package com.nttdata.domain.contract;

import com.nttdata.domain.models.ExchangeDto;
import io.smallrye.mutiny.Uni;

public interface ExchangeRepository {
  Uni<ExchangeDto> addPetition(ExchangeDto exchangeDto);
  Uni<ExchangeDto> findByNumberTransaction(ExchangeDto exchangeDto);
}
