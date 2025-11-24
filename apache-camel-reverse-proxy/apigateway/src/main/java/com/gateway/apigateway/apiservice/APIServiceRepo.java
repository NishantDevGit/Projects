package com.gateway.apigateway.apiservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gateway.apigateway.controller.model.APIService;

@Repository
public interface APIServiceRepo extends JpaRepository<APIService, String> {

}
