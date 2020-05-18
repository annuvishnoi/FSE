kubectl apply -f mysql-database-data-volume-persistentvolumeclaim.yaml,mysql-deployment.yaml,mysql-service.yaml
kubectl apply -f fse-pm-app-deployment.yaml,fse-pm-app-service.yaml
kubectl apply -f ingress.yaml