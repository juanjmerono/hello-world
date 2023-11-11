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

# Deploy Service

kubectl apply -f kpack/k8s-deployment.yaml

kubectl get pods

kubectl port-forward svc/my-spring-app-service 8080:80
kubectl port-forward svc/my-spring-app-service2 9090:80
kubectl port-forward svc/my-spring-app-service3 8888:80

# Update Source

You could change main branch repo source code or push another source code image to registry and then

kubectl rollout restart deployment my-spring-app
kubectl rollout restart deployment my-spring-app2
kubectl rollout restart deployment my-spring-app3

# Shutdown

kind delete cluster --name kpack
