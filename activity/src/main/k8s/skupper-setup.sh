kubectl create ns benelux
kubectl create ns cee
kubectl create ns uki

kubectl apply -f backend-benelux.yml -n benelux
kubectl annotate service activity skupper.io/proxy=http -n benelux
kubectl config set-context $(kubectl config current-context) --namespace=benelux
kubectl apply -f frontend.yml -n benelux
skupper init --console-auth unsecured -n benelux
skupper token create token.yaml -t cert

kubectl config set-context $(kubectl config current-context) --namespace=cee
kubectl apply -f backend-cee.yml -n cee
kubectl annotate service activity skupper.io/proxy=http -n cee
skupper init
skupper link create token.yaml

kubectl config set-context $(kubectl config current-context) --namespace=uki
kubectl apply -f backend-uki.yml -n uki
kubectl annotate service activity skupper.io/proxy=http -n uki
skupper init
skupper link create token.yaml
