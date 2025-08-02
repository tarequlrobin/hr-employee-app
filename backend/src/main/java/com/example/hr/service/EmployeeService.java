package com.example.hr.service;

import com.example.hr.model.Employee;
import com.example.hr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }


    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }

    public List<Employee> importEmployeesFromXml(MultipartFile file) {
        List<Employee> employees = new ArrayList<>();

        try {
            // Let parser detect encoding from the XML header
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file.getInputStream());
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("employee");

//            for (int i = 0; i < nodeList.getLength(); i++) {
//                Node node = nodeList.item(i);
//
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element element = (Element) node;
//                    Employee emp = new Employee();
//
//                    try {
//                        emp.setId(Integer.parseInt(element.getAttribute("id")));
//                        emp.setFirstName(getElementContent(element, "firstname"));
//                        emp.setLastName(getElementContent(element, "lastname"));
//                        emp.setTitle(getElementContent(element, "title"));
//                        emp.setDivision(getElementContent(element, "division"));
//                        emp.setBuilding(getElementContent(element, "building"));
//                        emp.setRoom(getElementContent(element, "room"));
//
//                        employees.add(emp);
//                    } catch (NumberFormatException e) {
//                        throw new RuntimeException("Invalid employee ID format in XML", e);
//                    }
//                }
//            }

            IntStream.range(0, nodeList.getLength())
                    .mapToObj(nodeList::item)
                    .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
                    .map(node -> {
                        Element element = (Element) node;
                        Employee emp = new Employee();
                        try {
                            emp.setId(Integer.parseInt(element.getAttribute("id")));
                            emp.setFirstName(getElementContent(element, "firstname"));
                            emp.setLastName(getElementContent(element, "lastname"));
                            emp.setTitle(getElementContent(element, "title"));
                            emp.setDivision(getElementContent(element, "division"));
                            emp.setBuilding(getElementContent(element, "building"));
                            emp.setRoom(getElementContent(element, "room"));
                            return emp;
                        } catch (NumberFormatException e) {
                            throw new RuntimeException("Invalid employee ID format in XML", e);
                        }
                    })
                    .forEach(employees::add);


            return employeeRepository.saveAll(employees);

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse XML file: " + e.getMessage(), e);
        }
    }

    private String getElementContent(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
}
