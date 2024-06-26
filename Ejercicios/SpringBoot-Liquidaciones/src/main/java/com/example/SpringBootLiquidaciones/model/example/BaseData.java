package com.example.SpringBootLiquidaciones.model.example;

import com.example.SpringBootLiquidaciones.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseData {
    private Map<String, Product> products;
    private Map<String, Risk> risks;

    private Map<String, Warranty> warranties;
    private List<Cliente> clientes;
    private List<Poliza> polizas;
    private List<Siniestro> siniestros;

    public BaseData() {
        createRisks();
        createWarranties();
        createProducts();
        createPolizas();
        createClients();
        createSiniestros();
    }

    public void altaSiniestro(Siniestro siniestro) {
        this.siniestros.add(siniestro);
    }

    private void createSiniestros() {
        this.siniestros = new ArrayList<>();
    }

    private void createPolizas() {
        this.polizas = new ArrayList<>();
        Poliza poliza = new Poliza();
        poliza.setCodigo("1");
        poliza.setProducto(products.get("HOGAR15"));
        poliza.setImporteCapitalesContratados(10000);
        this.polizas.add(poliza);
    }

    public Poliza findPolizaByCode(String code) {
        for (Poliza poliza : this.polizas) {
            if (poliza.getCodigo().equalsIgnoreCase(code)) {
                return poliza;
            }
        }
        return null;
    }

    public List<Poliza> findPolizasByUser(Cliente usuarioLogueado) {
        List<Poliza> polizas = new ArrayList<>();
        for (Poliza poliza : this.polizas) {
            if (poliza.getClienteAsociado().equals(usuarioLogueado)) {
                polizas.add(poliza);
            }
        }
        return polizas;
    }

    private void createClients() {
        this.clientes = new ArrayList<>();
        Cliente cliente = new Cliente();
        cliente.setNombre("Mario");
        cliente.setContraseña("123");
        cliente.setContacto("mario@gmail.com");

        Poliza poliza = new Poliza();
        poliza.setClienteAsociado(cliente);
        poliza.setProducto(products.get("HOGAR15"));
        poliza.setImporteCapitalesContratados(10000);
        poliza.setCodigo("2");
        this.polizas.add(poliza);
        cliente.setPoliza(polizas.get(0));

        this.clientes.add(cliente);
    }

    public Cliente getClienteByNombre(String nombre, String password) {
        for (Cliente cliente : this.clientes) {
            if (cliente.getNombre().equalsIgnoreCase(nombre) && cliente.getContraseña().equalsIgnoreCase(password)) {
                return cliente;
            }
        }
        return null;
    }

    private void createRisks() {
        this.risks = new HashMap<String, Risk>();
        Risk riskRoberyOutside = new Risk();
        riskRoberyOutside.setCode("RO");
        riskRoberyOutside.setName("Robery outside");
        Risk riskWind = new Risk();
        riskWind.setCode("WI");
        riskWind.setName("Wind Over 50Kmh");
        Risk riskMoisture = new Risk();
        riskWind.setCode("MS");
        riskWind.setName("Moisture");

        this.risks.put(riskWind.getCode(), riskWind);

    }

    private void createWarranties() {
        this.warranties = new HashMap<String, Warranty>();
        Warranty warrantyRoberyOutside = new Warranty();
        warrantyRoberyOutside.setCode("RO");
        warrantyRoberyOutside.setName("Robery outside");
        warrantyRoberyOutside.setWarrantyType(WarrantyType.CONTENT);
        Warranty warrantyRoof = new Warranty();
        warrantyRoof.setCode("RF");
        warrantyRoof.setName("Roof");
        warrantyRoof.setWarrantyType(WarrantyType.BUILDING);
        Warranty warrantyGeneralBuilding = new Warranty();
        warrantyGeneralBuilding.setCode("GB");
        warrantyGeneralBuilding.setName("General Building");
        warrantyGeneralBuilding.setWarrantyType(WarrantyType.BUILDING);
        Warranty warrantyHomeAppliances = new Warranty();
        warrantyHomeAppliances.setCode("HA");
        warrantyHomeAppliances.setName("Home Appliances");
        warrantyHomeAppliances.setWarrantyType(WarrantyType.CONTENT);

        this.warranties.put(warrantyRoberyOutside.getCode(), warrantyRoberyOutside);
        this.warranties.put(warrantyRoof.getCode(), warrantyRoof);
        this.warranties.put(warrantyGeneralBuilding.getCode(), warrantyGeneralBuilding);
        this.warranties.put(warrantyHomeAppliances.getCode(), warrantyHomeAppliances);
    }

    private void createProducts() {
        this.products = new HashMap<String, Product>();
        Product product = new Product();
        product.setCode("HOGAR15");
        product.setName("AXA Hogar 15");

        product.setProductWarranties(this.createProductWarranties());

        this.products.put(product.getCode(), product);
    }

    private List<ProductWarranty> createProductWarranties() {
        List<ProductWarranty> warranties = new ArrayList<ProductWarranty>();

        ProductWarranty warrantyRoberyOutside = new ProductWarranty();
        warrantyRoberyOutside.setExcluded(false);
        warrantyRoberyOutside.setCapitalInsured(300.0);
        warrantyRoberyOutside.setRisk(this.risks.get("RO"));
        warrantyRoberyOutside.setPaymentType(PaymentType.PRIMER_RIESGO);
        warrantyRoberyOutside.setWarranty(this.warranties.get("RO"));
        ProductWarranty warrantyRoberyOutsideRoof = new ProductWarranty();
        warrantyRoberyOutsideRoof.setExcluded(true);
        warrantyRoberyOutsideRoof.setRisk(this.risks.get("RO"));
        warrantyRoberyOutsideRoof.setWarranty(this.warranties.get("RF"));
        ProductWarranty warrantyRoberyGeneralBuilding = new ProductWarranty();
        warrantyRoberyGeneralBuilding.setExcluded(true);
        warrantyRoberyGeneralBuilding.setRisk(this.risks.get("RO"));
        warrantyRoberyGeneralBuilding.setWarranty(this.warranties.get("GB"));
        ProductWarranty warrantyRoberyOutsideHomeAppliance = new ProductWarranty();
        warrantyRoberyOutsideHomeAppliance.setExcluded(true);
        warrantyRoberyOutsideHomeAppliance.setRisk(this.risks.get("RO"));
        warrantyRoberyOutsideHomeAppliance.setWarranty(this.warranties.get("HA"));

        warranties.add(warrantyRoberyOutside);
        warranties.add(warrantyRoberyOutsideRoof);
        warranties.add(warrantyRoberyGeneralBuilding);
        warranties.add(warrantyRoberyOutsideHomeAppliance);

        ProductWarranty warrantyWindRoof = new ProductWarranty();
        warrantyWindRoof.setExcluded(false);
        warrantyWindRoof.setCapitalInsured(1300);
        warrantyWindRoof.setRisk(this.risks.get("WI"));
        warrantyWindRoof.setPaymentType(PaymentType.REPOSICION_NUEVO);
        warrantyWindRoof.setWarranty(this.warranties.get("RF"));
        ProductWarranty warrantyWindRoberyOutside = new ProductWarranty();
        warrantyWindRoberyOutside.setExcluded(true);
        warrantyWindRoberyOutside.setRisk(this.risks.get("WI"));
        warrantyWindRoberyOutside.setWarranty(this.warranties.get("RO"));
        ProductWarranty warrantyWindGeneralBuilding = new ProductWarranty();
        warrantyWindGeneralBuilding.setExcluded(true);
        warrantyWindGeneralBuilding.setRisk(this.risks.get("WI"));
        warrantyWindGeneralBuilding.setWarranty(this.warranties.get("GB"));
        ProductWarranty warrantyWindHomeAppliance = new ProductWarranty();
        warrantyWindHomeAppliance.setExcluded(true);
        warrantyWindHomeAppliance.setRisk(this.risks.get("WI"));
        warrantyWindHomeAppliance.setWarranty(this.warranties.get("HA"));

        warranties.add(warrantyWindRoof);
        warranties.add(warrantyWindRoberyOutside);
        warranties.add(warrantyWindGeneralBuilding);
        warranties.add(warrantyWindHomeAppliance);

        ProductWarranty warrantyMoistureRoof = new ProductWarranty();
        warrantyMoistureRoof.setExcluded(false);
        warrantyMoistureRoof.setRisk(this.risks.get("MS"));
        warrantyMoistureRoof.setWarranty(this.warranties.get("RF"));
        ProductWarranty warrantyMoistureRoberyOutside = new ProductWarranty();
        warrantyMoistureRoberyOutside.setExcluded(true);
        warrantyMoistureRoberyOutside.setRisk(this.risks.get("MS"));
        warrantyMoistureRoberyOutside.setWarranty(this.warranties.get("RO"));
        ProductWarranty warrantyMoistureGeneralBuilding = new ProductWarranty();
        warrantyRoberyOutsideRoof.setExcluded(false);
        warrantyRoberyOutsideRoof.setCapitalInsured(20000);
        warrantyRoberyOutsideRoof.setRisk(this.risks.get("MS"));
        warrantyRoberyOutsideRoof.setWarranty(this.warranties.get("GB"));
        ProductWarranty warrantyMoistureHomeAppliance = new ProductWarranty();
        warrantyMoistureHomeAppliance.setExcluded(false);
        warrantyMoistureHomeAppliance.setCapitalInsured(3000);
        warrantyMoistureHomeAppliance.setRisk(this.risks.get("MS"));
        warrantyMoistureHomeAppliance.setWarranty(this.warranties.get("HA"));

        warranties.add(warrantyMoistureRoof);
        warranties.add(warrantyMoistureRoberyOutside);
        warranties.add(warrantyMoistureGeneralBuilding);
        warranties.add(warrantyMoistureHomeAppliance);

        return warranties;

    }
}
