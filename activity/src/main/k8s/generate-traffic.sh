#POD1=$(kubectl get pod -l app=activity -l version=v1  -o jsonpath="{.items[0].metadata.name}")
#POD2=$(kubectl get pod -l app=activity -l version=v2  -o jsonpath="{.items[0].metadata.name}")

export GATEWAY_URL=istio-ingressgateway-istio-system.rhd-ams03-oct24-427237465-4c50a18a6ae19b704aa10d04d75751f8-0000.ams03.containers.appdomain.cloud


for i in {1..90}
do
curl $GATEWAY_URL/activity/limited/5881028
sleep 1
done