# CompassUOL-SP-Challenge-03-Squad-8-DaviFerreira
This is a repository to the Challenge 3 from Compass internship


# To Run the Ms's you will need to:

## Run the docker image of RabbitMQ:
  with this COMAND: docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.12-management

## Run the script file inside the MySql database
  The script has a admin who has authorization to all methods inside the product ms
  with login: bob@gmail.com and bob123
  Also have some seeding informations for the data base.

## Run all the microservices:
  After that you will get your JWT token and use it to have access in the requests.

