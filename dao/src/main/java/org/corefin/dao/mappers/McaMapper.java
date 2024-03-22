package org.corefin.dao.mappers;


import org.corefin.dto.LoanDto;
import org.corefin.dto.McaDto;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

// Converts the raw database object ResultSet into the LoanDto object
public class McaMapper implements RowMapper<McaDto> {

    @Override
    public McaDto map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new McaDto(
                rs.getString("loan_id"),
                rs.getInt("term"),
                rs.getBigDecimal("originated_amount"),
                rs.getString("currency"),
                rs.getBigDecimal("factor_rate"),
                rs.getBigDecimal("holdback_amount"),
                rs.getBigDecimal("holdback_percentage"),
                rs.getString("holdback_type"),
                rs.getString("external_reference"),
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate(),
                rs.getString("status"), // CREATED, IN_PROGRESS, CLOSED
                rs.getString("timezone"),
                rs.getString("region"),
                rs.getString("state")
        );
    }
}
