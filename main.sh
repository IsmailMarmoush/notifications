#!/bin/bash

subscriptions_url=http://localhost:9000/notifications/subscriptions

create() {
  curl -v -d '{"userId":"0", "frequencyVM":"one_second"}' -H "Content-Type: application/json" POST $subscriptions_url
}

update() {
  id=$1
  frequency=$2
  curl -v -d "{\"userId\":\"${id}\", \"frequencyVM\":\"${frequency}\"}" -H "Content-Type: application/json" POST $subscriptions_url
}

find() {
  startMillis=$1
  endMillis=$(date +%s%3N)
  curl -v "${subscriptions_url}?startMillis=${startMillis}&endMillis=${endMillis}"
}
"$@"
