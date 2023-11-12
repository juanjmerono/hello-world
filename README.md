# hello-world
Hello world SpringBoot

# Kind

kind create cluster --name kpack --image kindest/node:v1.24.7

Kubernetes 1.24 untaint control-plan node (to allow deployments)
kubectl taint nodes --all node-role.kubernetes.io/control-plane-

# Deploy Kpack

kubectl apply -f kpack/install/release-0.7.5.yaml
#kubectl apply -f kpack/install/release-0.12.2.yaml (Need K8s 1.25+)

# Deploy Secret

kubectl create secret docker-registry tutorial-registry-credentials \
    --docker-username=uuuuuu \
    --docker-password=xxxxxx \
    --docker-server=https://index.docker.io/v1/ \
    --namespace default

# Deploy KPack files

kubectl apply -f kpack/kpack.yaml

kubectl get clusterstacks
#kubectl get clusterbuilders
kubectl get clusterstores
kubectl get builders
kubectl get builds
kubectl get images
kubectl get pods

You can check lifecycle phases log

kubectl logs tutorial-image-build-1-build-pod -c detect
kubectl logs tutorial-image-build-1-build-pod -c build

# Inspect generated image

Install pack cli

Check Software Bill of Materials

pack sbom download yoanyo/hello-world -o target --remote

Inside targe/layers you can inspect details of jvm and all libs and vesions included in java app.

# Deploy Service

kubectl apply -f kpack/k8s-deployment.yaml

kubectl get pods

kubectl port-forward svc/my-spring-app-service 8080:80
kubectl port-forward svc/my-spring-app-service2 8080:80
kubectl port-forward svc/my-spring-app-service3 8080:80

# Update Source

You could change main branch repo source code to launch another build

kubectl rollout restart deployment my-spring-app
kubectl rollout restart deployment my-spring-app2

If you push another source code image to registry nothing happends (install kp cli)

kp image tutorial-image3 trigger

kubectl rollout restart deployment my-spring-app3

# Shutdown

kind delete cluster --name kpack
