
kubectl apply -f activity/src/main/k8s/backend-benelux-v2.yml

kubectl apply -f activity/src/main/k8s/backend-faulty-probe.yml

####uncomment these lines if you are not using OpenShift####
#kubectl apply -f <(istioctl kube-inject -f activity/src/main/k8s/backend-benelux-v2.yml)
#kubectl apply -f <(istioctl kube-inject -f activity/src/main/k8s/backend-benelux.yml)

kubectl apply -f activity/src/main/k8s/gateway.yaml

###ONLY for OpenShift; comment the line below if working in a different environment
export GATEWAY_URL=$(kubectl get route istio-ingressgateway -n istio-system -o jsonpath='{.status.ingress[0].host}')

###If you are not using OpenShift, please  uncomment the following line
#export GATEWAY_URL=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.status.loadBalancer.ingress[0].hostname}').

kubectl delete virtualservice activity
kubectl delete destinationrule activity

kubectl apply -f activity/src/main/k8s/destination-rule-lb-activity-v1-v2.yml
kubectl apply -f activity/src/main/k8s/virtual-service-activity-default.yml

siege -r 10 -c 4 -v  $GATEWAY_URL/activity