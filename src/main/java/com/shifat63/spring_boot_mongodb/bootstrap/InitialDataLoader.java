package com.shifat63.spring_boot_mongodb.bootstrap;
// Author: Shifat63

import com.shifat63.spring_boot_mongodb.model.Brand;
import com.shifat63.spring_boot_mongodb.model.Employee;
import com.shifat63.spring_boot_mongodb.model.Product;
import com.shifat63.spring_boot_mongodb.model.Showroom;
import com.shifat63.spring_boot_mongodb.services.service.BrandService;
import com.shifat63.spring_boot_mongodb.services.service.EmployeeService;
import com.shifat63.spring_boot_mongodb.services.service.ProductService;
import com.shifat63.spring_boot_mongodb.services.service.ShowroomService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InitialDataLoader implements CommandLineRunner {

    private ProductService productService;
    private BrandService brandService;
    private EmployeeService employeeService;
    private ShowroomService showroomService;

    private List<Brand> brandList = new ArrayList<>();
    private List<Showroom> showroomList = new ArrayList<>();

    public InitialDataLoader(ProductService productService, BrandService brandService, EmployeeService employeeService, ShowroomService showroomService) {
        this.productService = productService;
        this.brandService = brandService;
        this.employeeService = employeeService;
        this.showroomService = showroomService;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBrandData();
        loadShowroomData();
        loadProductData();
        loadEmployeeData();
    }

    private void loadEmployeeData() throws Exception
    {
        //START: Employee data
        Employee ema = new Employee();
        ema.setFirstName("Khotimakhon");
        ema.setLastName("Ema");
        ema.setIdCardNo("EMA09098");
        ema.setBirthDate(LocalDate.now().minus((24*365), ChronoUnit.DAYS));
        ema.setJoiningDate(LocalDate.now().minus((1*365), ChronoUnit.DAYS));
        ema.setShowroom(showroomList.get(0));
        employeeService.saveOrUpdate(ema);

        Employee zuha = new Employee();
        zuha.setFirstName("Zuha");
        zuha.setLastName("Karim");
        zuha.setIdCardNo("ZUH45654");
        zuha.setBirthDate(LocalDate.now().minus((23*365), ChronoUnit.DAYS));
        zuha.setJoiningDate(LocalDate.now().minus((2*365), ChronoUnit.DAYS));
        zuha.setShowroom(showroomList.get(1));
        employeeService.saveOrUpdate(zuha);

        Employee joki = new Employee();
        joki.setFirstName("Adam");
        joki.setLastName("Zoki");
        joki.setIdCardNo("ZOK34543");
        joki.setBirthDate(LocalDate.now().minus((25*365), ChronoUnit.DAYS));
        joki.setJoiningDate(LocalDate.now().minus((3*365), ChronoUnit.DAYS));
        joki.setShowroom(showroomList.get(0));
        employeeService.saveOrUpdate(joki);

        Employee fish = new Employee();
        fish.setFirstName("Bryan");
        fish.setLastName("Fish");
        fish.setIdCardNo("FIS34093");
        fish.setBirthDate(LocalDate.now().minus((27*365), ChronoUnit.DAYS));
        fish.setJoiningDate(LocalDate.now());
        fish.setShowroom(showroomList.get(1));
        employeeService.saveOrUpdate(fish);
        //END: Employee data
    }

    private void loadBrandData() throws Exception
    {
        //START: Brand data
        Brand lenovo = new Brand();
        lenovo.setName("Lenovo");
        lenovo.setDescription("Lenovo description");
        brandList.add(brandService.saveOrUpdate(lenovo));

        Brand intel = new Brand();
        intel.setName("Intel");
        intel.setDescription("Intel description");
        brandList.add(brandService.saveOrUpdate(intel));

        Brand acer = new Brand();
        acer.setName("Acer");
        acer.setDescription("Acer description");
        brandList.add(brandService.saveOrUpdate(acer));

        Brand dell = new Brand();
        dell.setName("Dell");
        dell.setDescription("Dell description");
        brandList.add(brandService.saveOrUpdate(dell));
        //END: Brand data
    }

    private void loadShowroomData() throws Exception
    {
        //START: Showroom data
        Showroom bonnShowroom = new Showroom();
        bonnShowroom.setName("Bonn Showroom");
        bonnShowroom.setAddress("Bonn showroom address");
        Set<Employee> employeeSetBonn = new HashSet<>();
        showroomList.add(showroomService.saveOrUpdate(bonnShowroom));

        Showroom berlinShowroom = new Showroom();
        berlinShowroom.setName("Berlin Showroom");
        berlinShowroom.setAddress("Berlin showroom address");
        Set<Employee> employeeSetBerlin = new HashSet<>();
        showroomList.add(showroomService.saveOrUpdate(berlinShowroom));
        //END: Showroom data
    }

    private void loadProductData() throws Exception
    {
        //START: Product data
        Product lenovoLaptop = new Product();
        lenovoLaptop.setName("Lenovo Laptop");
        lenovoLaptop.setBrand(brandList.get(0));
        lenovoLaptop.setDescription("Lenovo Laptop description");
        lenovoLaptop.setAvailable(true);
        lenovoLaptop.setPrice(500.65);
        Set<Showroom> showroomSetLenovoLaptop = new HashSet<>();
        showroomSetLenovoLaptop.add(showroomList.get(0));
        showroomSetLenovoLaptop.add(showroomList.get(1));
        lenovoLaptop.setShowroomSet(showroomSetLenovoLaptop);
        productService.saveOrUpdate(lenovoLaptop);

        Product acerLaptop = new Product();
        acerLaptop.setName("Acer Laptop");
        acerLaptop.setBrand(brandList.get(2));
        acerLaptop.setDescription("Acer Laptop description");
        acerLaptop.setAvailable(true);
        acerLaptop.setPrice(400.75);
        Set<Showroom> showroomSetAcerLaptop = new HashSet<>();
        showroomSetAcerLaptop.add(showroomList.get(0));
        showroomSetAcerLaptop.add(showroomList.get(1));
        acerLaptop.setShowroomSet(showroomSetAcerLaptop);
        productService.saveOrUpdate(acerLaptop);

        Product dellLaptop = new Product();
        dellLaptop.setName("Dell Laptop");
        dellLaptop.setBrand(brandList.get(3));
        dellLaptop.setDescription("Dell Laptop description");
        dellLaptop.setAvailable(true);
        dellLaptop.setPrice(450.50);
        Set<Showroom> showroomSetDellLaptop = new HashSet<>();
        showroomSetDellLaptop.add(showroomList.get(0));
        showroomSetDellLaptop.add(showroomList.get(1));
        dellLaptop.setShowroomSet(showroomSetDellLaptop);
        productService.saveOrUpdate(dellLaptop);

        Product intelProcessor = new Product();
        intelProcessor.setName("Intel Processor");
        intelProcessor.setBrand(brandList.get(1));
        intelProcessor.setDescription("Intel Processor description");
        intelProcessor.setAvailable(true);
        intelProcessor.setPrice(210.50);
        Set<Showroom> showroomSetIntelProcessor = new HashSet<>();
        showroomSetIntelProcessor.add(showroomList.get(0));
        showroomSetIntelProcessor.add(showroomList.get(1));
        intelProcessor.setShowroomSet(showroomSetIntelProcessor);
        productService.saveOrUpdate(intelProcessor);

        Product intelMotherBoard = new Product();
        intelMotherBoard.setName("Intel MotherBoard");
        intelMotherBoard.setBrand(brandList.get(1));
        intelMotherBoard.setDescription("Intel MotherBoard description");
        intelMotherBoard.setAvailable(false);
        intelMotherBoard.setPrice(110.50);
        Set<Showroom> showroomSetIntelMotherBoard = new HashSet<>();
        showroomSetIntelMotherBoard.add(showroomList.get(1));
        intelMotherBoard.setShowroomSet(showroomSetIntelMotherBoard);
        productService.saveOrUpdate(intelMotherBoard);

        //END: Product data
    }
}
