pg:
	docker run \
		--name connect4-postgres \
		-p ${PGPORT}:5432 \
		-e POSTGRES_PASSWORD=${PGPASSWORD} \
		-e POSTGRES_USER=${PGUSER} \
		-e POSTGRES_DB=${PGDATABASE} \
		-e TZ=Europe/Berlin \
		--restart=unless-stopped \
		-d connect4-postgres

pg_image:
	docker build -t connect4-postgres .

# clean:
#	docker rm -f connect4-postgres && rm -r pgdata && mkdir pgdata

clean:
	docker rm -f connect4-postgres

start: pg_image pg