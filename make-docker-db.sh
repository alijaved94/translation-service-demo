# makes a docker db you can use for local testing.
# also deletes any existing docker db with the same name


# Check if a container named 'translation_db' exists
container_id=$(docker ps -a -q -f name=translation_db)

# If the container exists, delete it
if [ -n "$container_id" ]; then
  echo "Container 'translation_db' exists. Deleting it..."
  docker rm -f translation_db
else
  echo "Creating new container 'translation_db'..."
fi


docker run -p5433:5432 --name translation_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=translation_db -d postgres:12