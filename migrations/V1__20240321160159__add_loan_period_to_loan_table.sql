-- Create new table 'loan'
CREATE TABLE mca (
                      mca_id VARCHAR(36) PRIMARY KEY,
                      term INT NOT NULL, # set to 0, if variable term
                      originated_amount DECIMAL(32, 2) NOT NULL,
                      currency CHAR(3) NOT NULL,
                      factor_rate DECIMAL(13, 10) NOT NULL,
                      holdback_amount DECIMAL(32, 2) NOT NULL, # estimated hold back amount
                      holdback_percentage DECIMAL(13, 10) NOT NULL, # percentage for hold back based on variable income
                      holdback_type VARCHAR(36) NOT NULL, # prefer either AMOUNT or VARIABLE
                      external_reference VARCHAR(36),
                      start_date DATE NOT NULL,
                      status VARCHAR(36) NOT NULL,
                      timezone VARCHAR(36) NOT NULL,
                      region VARCHAR(3) NOT NULL,
                      state VARCHAR(2),
                      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
