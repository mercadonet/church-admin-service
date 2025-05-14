awslocal dynamodb create-table\
 --table-name church-admin \
 --key-schema AttributeName=churchId,KeyType=HASH \
 --attribute-definitions AttributeName=id,AttributeType=S \
 --billing-mode PAY_PER_REQUEST \
 --region us-east-1