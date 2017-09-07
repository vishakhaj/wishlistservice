//package com.wishlistservice.web.resource;
//
//import static org.springframework.hateoas.mvc.BasicLinkBuilder.linkToCurrentMapping;
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.validation.constraints.NotNull;
//
//import org.springframework.hateoas.ResourceSupport;
//import org.springframework.hateoas.mvc.BasicLinkBuilder;
//
//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.wishlistservice.controller.WishlistController;
//import com.wishlistservice.domain.Item;
//import com.wishlistservice.viewbean.WishlistViewBean;
//
//public class WishlistResource extends ResourceSupport {
//
//	private WishlistViewBean wishlistViewBean;
//
//	private List<Item> items;
//
//	private boolean addedToWishlist;
//	
//	//private List<WishlistResource> wishlistResource;
//	
//	private WishlistResource wishlistResource;
//
//	private List<WishlistViewBean> wishlists;
//	
//	private boolean emptyViewBean;
//	
//	public boolean isEmptyViewBean() {
//		return emptyViewBean;
//	}
//
//	public void setEmptyViewBean(boolean emptyViewBean) {
//		this.emptyViewBean = emptyViewBean;
//	}
//
//	public WishlistResource() {
//		super();
//	}
//
//	public WishlistResource(final List<Item> items, final boolean addedToWishlist) {
//		super();
//
//		this.items = items;
//		this.addedToWishlist = addedToWishlist;
//
//		if (addedToWishlist) {
//			this.add(linkToCurrentMapping().slash("/receipt").slash("test").withRel("receipt"));
//		}
//	}
//	
//	@JsonCreator
//	public WishlistResource(final WishlistViewBean wishlistViewBean) {
//		
//		this.wishlistViewBean = wishlistViewBean;
//		System.out.println("code goes here");
//		
//		if(wishlistViewBean!=null){
//			this.add(linkTo(WishlistController.class).slash("/users/user").slash(wishlistViewBean.getUserId())
//					.slash("/wishlists/wishlist").slash(wishlistViewBean.getWishlistId()).withSelfRel());
//			this.add(BasicLinkBuilder.linkToCurrentMapping().slash("api/users/user").slash(wishlistViewBean.getUserId())
//					.slash("wishlists").withRel("view-your-wishlist"));
//			this.add(BasicLinkBuilder.linkToCurrentMapping().slash("api/users/user").slash(wishlistViewBean.getUserId())
//					.slash("wishlists").withRel("delete-all-wishlists"));
//		}
//		
//	}
//	
//	public WishlistResource(WishlistResource wishlistResource){
//		this.wishlistResource = wishlistResource;
//		//this.add(linkTo(WishlistController.class).slash("test").withRel("test"));
//	}
//
//	public WishlistViewBean getWishlist() {
//		return wishlistViewBean;
//	}
//	
//	public boolean isAddedToWishlist() {
//		return addedToWishlist;
//	}
//
//	public void setAddedToWishlist(boolean addedToWishlist) {
//		this.addedToWishlist = addedToWishlist;
//	}
//	
//	public WishlistResource getWishlistResource() {
//		return wishlistResource;
//	}
//
//	public void setWishlistResource(WishlistResource wishlistResource) {
//		this.wishlistResource = wishlistResource;
//	}
//
//	@Override
//	public String toString() {
//		return "WishlistResource [wishlist=" + wishlistViewBean + "]";
//	}
//}
