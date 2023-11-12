# hello-world
Hello world SpringBoot

# Kind

kind create cluster --name kpack --image kindest/node:v1.24.7

Kubernetes 1.24 untaint control-plan node (to allow deployments)
kubectl taint nodes --all node-role.kubernetes.io/control-plane-

# Deploy Kpack

kubectl apply -f kpack/install/release-0.9.5.yaml
#kubectl apply -f kpack/install/release-0.7.5.yaml (too old)
#kubectl apply -f kpack/install/release-0.12.2.yaml (Need K8s 1.25+)

# Deploy Secret

kubectl create secret docker-registry tutorial-registry-credentials \
    --docker-username=uuuuuu \
    --docker-password=xxxxxx \
    --docker-server=https://index.docker.io/v1/ \
    --namespace default

# Deploy KPack files

kubectl apply -f kpack/manifests/kpack-service-account.yaml
kubectl apply -f kpack/manifests/kpack-java-builder.yaml
kubectl apply -f kpack/manifests/kpack-java-native-builder.yaml

kubectl get clusterstacks
#kubectl get clusterbuilders
kubectl get clusterstores
kubectl get builders
kubectl get pods -A

# Deploy regular image

kubectl apply -f kpack/manifests/kpack-image.yaml
#kubectl apply -f kpack/manifests/kpack-image-native.yaml
#kubectl apply -f kpack/manifests/kpack-image-source.yaml

kubectl get builds
kubectl get images

You can check lifecycle phases log

kubectl logs tutorial-image-build-1-build-pod -c detect
kubectl logs tutorial-image-build-1-build-pod -c build

Even go inside build container

kubectl exec -it tutorial-image-build-1-build-pod -c build sh

Or using kp cli

kp build logs tutorial-image -b 1
kp image status tutorial-image

# Inspect generated image

Install pack cli

Check Software Bill of Materials

pack sbom download yoanyo/hello-world -o target --remote

Inside targe/layers you can inspect details of jvm and all libs and vesions included in java app.

# Deploy Service

kubectl apply -f kpack/manifests/k8s-image-deployment.yaml
#kubectl apply -f kpack/manifests/k8s-image-native-deployment.yaml

kubectl get pods

kubectl port-forward svc/my-spring-app-service 8080:80
#kubectl port-forward svc/my-spring-app-native-service 8080:80

# Update Source

You could change main branch repo source code to launch another build

kubectl rollout restart deployment my-spring-app
#kubectl rollout restart deployment my-spring-app-native

# Create image source (optional)

docker build -t yoanyo/hello-world-src .
docker push yoanyo/hello-world-src

kubectl apply -f kpack/manifests/k8s-image-source-deployment.yaml

kubectl port-forward svc/my-spring-app-source-service 8080:80

If you push another source code image to registry nothing happends (install kp cli)

kp image tutorial-image-source trigger

kubectl rollout restart deployment my-spring-app-source

# Update JDK or TOMCAT

Change env variables in image manifesto (only to change major version)

# Rebase RUN image

Change stack run image

# Shutdown

kind delete cluster --name kpack
