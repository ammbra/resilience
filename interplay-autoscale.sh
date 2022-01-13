export GATEWAY_URL=$(kubectl get route istio-ingressgateway -n istio-system -o jsonpath='{.status.ingress[0].host}')

kubectl apply -f activity/src/main/k8s/destination-rule-activity-v1-v2.yml
kubectl apply -f activity/src/main/k8s/virtual-service-activity-default.yml

kubectl apply -f activity/src/main/k8s/backend-autoscale.yml

kubectl autoscale deployment activity --cpu-percent=50 --min=1 --max=10


hey -c 20 -z 5m  "http://"$GATEWAY_URL/activity/limited/5881028
