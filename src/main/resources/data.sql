DROP TABLE IF EXISTS fare_lookup_table;

CREATE TABLE fare_lookup_table (
  id INT AUTO_INCREMENT PRIMARY KEY,
  from_zone INT NOT NULL,
  to_zone INT NOT NULL,
  peak_hour_rate INT NOT NULL,
  off_peak_hour_rate INT NOT NULL
);

INSERT INTO fare_lookup_table (from_zone,to_zone,peak_hour_rate,off_peak_hour_rate) VALUES
  (1, 1, 30, 25),
  (1, 2, 35, 30),
  (2, 1, 35, 30),
  (2, 2, 25, 20);
