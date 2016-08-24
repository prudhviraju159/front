package com.niit.shoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.niit.shoppingcart.dao.CategoryDAO;
import com.niit.shoppingcart.dao.ProductDAO;
import com.niit.shoppingcart.dao.SupplierDAO;
import com.niit.shoppingcart.model.Category;
import com.niit.shoppingcart.model.Product;
import com.niit.shoppingcart.model.Supplier;

import ch.qos.logback.core.util.FileUtil;

@Controller
public class ProductController {

	
	@Autowired(required = true)
	private ProductDAO productDAO;
	
	@Autowired(required = true)
	private CategoryDAO categoryDAO;
	
	@Autowired(required = true)
	private SupplierDAO supplierDAO;
	
	private String path="D:\\shoppingcart\\img";
	
	
	
	@RequestMapping(value="/products", method= RequestMethod.GET)
	public String listProducts(Model model){
		model.addAttribute("product", new Product());
		model.addAttribute("category",new Category());
		model.addAttribute("supplier",new Supplier());
		model.addAttribute("productList", this.productDAO.list());
		model.addAttribute("categoryList", this.categoryDAO.list());
		model.addAttribute("supplierList", this.supplierDAO.list());
		return "product";
		
	}
	
	@RequestMapping(value = "/product/add",method =RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product){
		Category category = categoryDAO.getByName(product.getCategory().getName());
		categoryDAO.saveOrUpdate(category);
		
		Supplier supplier= supplierDAO.getByName(product.getSupplier().getName());
		supplierDAO.saveOrUpdate(supplier);
		
		
		product.setCategory(category);
		product.setSupplier(supplier);
		
		
		product.setCategory_id(category.getId());
		product.setSupplier_id(supplier.getId());
		productDAO.saveOrUpdate(product);
		
		
		MultipartFile file = product.getImage();
		FileUtil.upload(path, file,product.getId()+".jpg");
		return "redirect/products";
	
	
	}
	
	@RequestMapping("product/remove/{id}")
	public String removeProduct(@PathVariable("id") String id, ModelMap model) throws Exception{
		
	try{
		productDAO.delete(id);
		model.addAttribute("message","Successfully Added");
		}	catch(Exception e){
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
		}
		return "category";
		
		
	}
	
	
}
