CREATE TABLE budgets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    event_id BIGINT NOT NULL,
    venue_cost DECIMAL(10, 2),
    beverage_cost_per_person DECIMAL(10, 2),
    guest_number INT,
    total_budget DECIMAL(10, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_budget_event FOREIGN KEY (event_id) REFERENCES events (event_id)
);