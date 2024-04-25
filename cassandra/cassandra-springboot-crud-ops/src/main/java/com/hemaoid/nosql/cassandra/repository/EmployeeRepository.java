package com.hemaoid.nosql.cassandra.repository;

import com.hemaoid.nosql.cassandra.entity.Employee;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CassandraRepository<Employee, Integer> {
}
