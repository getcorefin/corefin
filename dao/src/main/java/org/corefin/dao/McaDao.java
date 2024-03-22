package org.corefin.dao;

import org.corefin.dao.mappers.McaMapper;
import org.corefin.dto.McaDto;
import org.corefin.dto.McaDto;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

// TODO: implement me
public class McaDao implements BaseDao<McaDto> {
    private Jdbi jdbi;
    public McaDao(Jdbi jdbi) {
        this.jdbi = jdbi;
        registerRowMapper();
    }
    // TODO: Pass in Handle here
    @Override
    public void insert(McaDto dto) {
       jdbi.useHandle(
               handle -> insertWithReturn(dto)
       );
    }

    public McaDto insertWithReturn(McaDto dto) {
        return jdbi.withHandle(
                handle -> insert(dto, handle)
        );
    }
    public McaDto insert(McaDto dto, Handle handle) {
        String insertQuery = """
                INSERT INTO mca (mca_id,
                                  term,
                                  originated_amount,
                                  currency,
                                  factor_rate,
                                  holdback_amount,
                                  holdback_percentage,
                                  holdback_type,
                                  target_interest_rate,
                                  effective_interest_rate,
                                  external_reference,
                                  start_date,
                                  end_date,
                                  status,
                                  timezone,
                                  region,
                                  state)
                VALUES (
                        :mca_id,
                        :term,
                        :originated_amount,
                        :currency,
                        :factor_rate,
                        :holdback_amount,
                        :holdback_percentage,
                        :holdback_type,
                        :external_reference,
                        :start_date,
                        :end_date,
                        :status,
                        :timezone,
                        :region,
                        :state)
                """;
        // TODO: check if row count == 0 -> that means an exception happened
        handle.createUpdate(insertQuery)
                .bind("loan_id", dto.mcaId())
                .bind("term", dto.term())
                .bind("originated_amount", dto.originatedAmount())
                .bind("currency", dto.currency())
                .bind("factor_rate", dto.factorRate())
                .bind("holdback_amount", dto.holdbackAmount())
                .bind("holdback_percentage", dto.holdbackPercentage())
                .bind("holdback_type", dto.holdbackType())
                .bind("external_reference", dto.externalReference())
                .bind("start_date", dto.startDate())
                .bind("end_date", dto.endDate())
                .bind("status", dto.status())
                .bind("timezone", dto.timezone())
                .bind("region", dto.region())
                .bind("state", dto.state())
                .execute();
        return findById(dto.mcaId());
    }

    @Override
    public McaDto findById(String mcaId) {
        return jdbi.withHandle(
                handle -> handle.createQuery("SELECT * FROM mca WHERE mca_id = :mca_id")
                        .bind("mca_id", mcaId)
                        .mapTo(McaDto.class)
                        .findOne()
                        .orElseThrow()
        );
    }
    @Override
    public void registerRowMapper() {
        jdbi.registerRowMapper(new McaMapper());
    }
}
