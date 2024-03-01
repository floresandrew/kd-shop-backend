package com.moditech.ecommerce.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moditech.ecommerce.dto.*;
import com.moditech.ecommerce.model.Order;
import com.moditech.ecommerce.model.Product;
import com.moditech.ecommerce.repository.OrderRepository;
import com.moditech.ecommerce.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    public void updateOrderStatus(String id, Order order) {
        Order setOrder = orderRepository.findById(id).orElse(null);
        System.out.println("eto ung order" + setOrder);
        if (setOrder != null) {
            String newStatus = order.getPaymentStatus();

            setOrder.setPaymentStatus(newStatus);
            orderRepository.save(setOrder);

            if (newStatus.equalsIgnoreCase("Paid")) {
                subtractProductsFromInventory(parseOrderList(setOrder.getOrderList()));
                updateProductSold(parseOrderList(setOrder.getOrderList()));
            }
        }
    }

    public OrderDto updateDeliveryStatus(String id, OrderDto orderBody) {
        Order order = orderRepository.findById(id).orElse(null);
        order.setDeliveryStatus(orderBody.getDeliveryStatus());
        orderRepository.save(order);
        return orderBody;
    }

    private void subtractProductsFromInventory(List<ProductDto> productQuantities) {
        for (ProductDto pq : productQuantities) {
            Optional<Product> productOptional = productRepository.findById(pq.getId());
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                System.out.println(product);
                product.setQuantity(product.getQuantity() - pq.getQuantity());
                productRepository.save(product);
            } else {
                log.error("error in subtractProductsFromInventory");
            }
        }
    }

    private void updateProductSold(List<ProductDto> productQuantities) {
        for (ProductDto pq : productQuantities) {
            Optional<Product> productOptional = productRepository.findById(pq.getId());
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                product.setSold(product.getSold() + pq.getQuantity());
                productRepository.save(product);
            } else {
                log.error("error in updating product sold");
            }
        }
    }

    private List<ProductDto> parseOrderList(String orderList) {
        try {
            if (orderList == null || orderList.isEmpty()) {
                return Collections.emptyList();
            }

            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(orderList, new TypeReference<List<ProductDto>>() {
            });
        } catch (IOException e) {
            System.out.print("Error");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Order> getOrderByEmail(String email) {
        return orderRepository.findByEmail(email);
    }

    public void uploadReceipt(String orderId, Order getOrder) {
        Order setOrder = orderRepository.findById(orderId).orElse(null);
        if (setOrder != null) {
            setOrder.setReceipt(getOrder.getReceipt());
            orderRepository.save(setOrder);
        }
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id).orElse(null);
    }

    public double getTotalPriceByStatus() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().mapToDouble(Order::getTotalPrice).sum();
    }

    public List<Map<String, Object>> getTotalSalesPerMonth() {
        List<Order> allOrders = orderRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM");
        return allOrders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getOrderDate().format(formatter),
                        Collectors.summingDouble(Order::getTotalPrice)))
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("orderDate", entry.getKey());
                    result.put("totalPrice", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }

    public List<OrderCountDto> getTop5Customers() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("email").count().as("orderCount").first("email").as("email"),
                Aggregation.sort(Sort.by(Sort.Order.desc("orderCount"))),
                Aggregation.limit(5));

        AggregationResults<OrderCountDto> result = mongoTemplate.aggregate(aggregation, "order", OrderCountDto.class);
        return result.getMappedResults();
    }
}