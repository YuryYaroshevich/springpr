drop procedure if exists add_to_event_statistics;
create procedure add_to_event_statistics(event_id int, request_type varchar(100))
	begin
		declare rows_inserted_num int;
        select count(*) from event_statistics es
			where es.event_id = event_id and es.request_type = request_type into rows_inserted_num;
		if rows_inserted_num = 0
			then insert into event_statistics(event_id, request_type, count)
				values(event_id, request_type, 1);
		else update event_statistics set count = count + 1
			where es.event_id = event_id and es.request_type = request_type;
		end if;
	end