serviceIP=$1
source /usr/local/falkon.r174/falkon.env 
falkon-worker-stdout.sh $serviceIP 50001
