#POD1=$(kubectl get pod -l app=activity -l version=v1  -o jsonpath="{.items[0].metadata.name}")
#POD2=$(kubectl get pod -l app=activity -l version=v2  -o jsonpath="{.items[0].metadata.name}")

export GATEWAY_URL=$1

for i in {1..30}
do
#curl -v $GATEWAY_URL/activity/timeout/5881028
curl -v $GATEWAY_URL/activity/notavailable/5881028

sleep 1
done