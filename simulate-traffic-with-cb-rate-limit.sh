
###ONLY for OpenShift; comment the line below if working in a different environment
export GATEWAY_URL=$(kubectl get route istio-ingressgateway -n istio-system -o jsonpath='{.status.ingress[0].host}')

###If you are not using OpenShift, please  uncomment the following line
#export GATEWAY_URL=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.status.loadBalancer.ingress[0].hostname}').

siege -r 10 -c 4 -v  $GATEWAY_URL/activity/limited/5881028