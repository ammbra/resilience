#POD1=$(kubectl get pod -l app=activity -l version=v1  -o jsonpath="{.items[0].metadata.name}")
#POD2=$(kubectl get pod -l app=activity -l version=v2  -o jsonpath="{.items[0].metadata.name}")

export GATEWAY_URL=$1


for i in {1..90}
do
curl $GATEWAY_URL/activity/limited/5881028
sleep 1
done