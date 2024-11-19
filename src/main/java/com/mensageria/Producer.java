package com.mensageria;

import com.mensageria.config.Config;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {

    private static final String QUEUE_NAME = Config.getProperty("amqp.queueName");
    private static final String URI = Config.getProperty("amqp.uri");

    public static void main(String[] args) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(URI);
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){
            channel.queueDeclare(QUEUE_NAME,true,false,false,null);
            System.out.println("Digite a mensagem: ");
            Scanner sc = new Scanner(System.in);
            String helloWorld = sc.nextLine();
            channel.basicPublish("",QUEUE_NAME,null,helloWorld.getBytes());
            System.err.println(" Enviado: '" + helloWorld + "'");
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
