CREATE OR REPLACE FUNCTION update_trip_actuality()
RETURNS TRIGGER AS $$
BEGIN
	IF NEW.date_of_departure < NOW() AND NEW.actuality = true THEN
		NEW.actuality = false;
END IF;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trip_actuality_trigger
    BEFORE INSERT OR UPDATE ON trip
    FOR EACH ROW
    EXECUTE FUNCTION update_trip_actuality()
