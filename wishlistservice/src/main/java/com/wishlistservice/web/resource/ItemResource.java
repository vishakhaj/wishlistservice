//package com.wishlistservice.web.resource;
//
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
//
//import javax.validation.constraints.NotNull;
//
//import org.springframework.hateoas.ResourceSupport;
//
//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.wishlistservice.controller.ItemController;
//import com.wishlistservice.domain.Item;
//
//public class ItemResource extends ResourceSupport {
//
//    private final Item item;
//    
//    @JsonCreator
//    public ItemResource(@NotNull final Item item) {
//        this.item = item;
//        this.add(linkTo(ItemController.class).slash("/items/item").slash(item.getItemId()).withSelfRel());
//    }
//    public Item getItem() {
//        return item;
//    }
//
//    @Override
//    public String toString() {
//        return "ItemResource [item=" + item + ", toString()=" + super.toString() + "]";
//    }
//
//}
