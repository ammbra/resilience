###ONLY for OpenShift; comment the line below if working in a different environment
export GATEWAY_URL=$(kubectl get route istio-ingressgateway -n istio-system -o jsonpath='{.status.ingress[0].host}')

###If you are not using OpenShift, please  uncomment the following line
#export GATEWAY_URL=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.status.loadBalancer.ingress[0].hostname}').

for i in {1..30}
do
curl -v $GATEWAY_URL/activity/timeout/5881028
#curl -v $GATEWAY_URL/activity/notavailable/5881028

sleep 1
done