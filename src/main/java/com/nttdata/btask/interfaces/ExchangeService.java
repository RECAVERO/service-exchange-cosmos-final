package com.nttdata.btask.interfaces;

import com.nttdata.domain.models.ExchangeDto;
import io.smallrye.mutiny.Uni;

public interface ExchangeService {
  Uni<ExchangeDto> addPetition(ExchangeDto exchangeDto);
  Uni<ExchangeDto> findByNumberTransaction(ExchangeDto exchangeDto);
}
