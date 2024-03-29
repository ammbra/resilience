kubectl create ns benelux
kubectl create ns cee
kubectl create ns uki
kubectl create ns dach

kubectl apply -f activity/src/main/k8s/backend-benelux.yml -n benelux
kubectl annotate service activity skupper.io/proxy=http -n benelux
kubectl config set-context $(kubectl config current-context) --namespace=benelux
kubectl apply -f hobby/src/main/k8s/frontend.yml -n benelux
skupper init --console-auth unsecured -n benelux
skupper token create token.yaml -t cert

kubectl config set-context $(kubectl config current-context) --namespace=cee
kubectl apply -f activity/src/main/k8s/backend-cee.yml -n cee
kubectl annotate service activity skupper.io/proxy=http -n cee
skupper init
skupper link create token.yaml

kubectl config set-context $(kubectl config current-context) --namespace=dach
kubectl apply -f activity/src/main/k8s/backend-dach.yml -n dach
kubectl annotate service activity skupper.io/proxy=http -n dach
skupper init
skupper link create token.yaml

kubectl config set-context $(kubectl config current-context) --namespace=uki
kubectl apply -f activity/src/main/k8s/backend-uki.yml -n uki
kubectl annotate service activity skupper.io/proxy=http -n uki
skupper init
skupper link create token.yaml
