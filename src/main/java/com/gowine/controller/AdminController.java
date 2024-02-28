package com.gowine.controller;

import com.gowine.constant.ItemSellStatus;
import com.gowine.dto.*;
import com.gowine.entity.Item;
import com.gowine.entity.Member;
import com.gowine.service.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    ItemService itemService;
    @Autowired
    ItemImgService itemImgService;
    @Autowired
    MemberService memberService;
    @Autowired
    OrderService orderService;
    @Autowired
    ReviewService reviewService;

    @Autowired
    ReviewImgService reviewImgService;

    @GetMapping("/admin/main")
    public String adminMainPage() {
        return "admins/main";
    }

    @GetMapping(value = "/admin/item/list")
    public String itemDashbord(Model model) {
        return "admins/item/list";
    }

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "admins/item/register";
    }

    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
                          @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {

        if(bindingResult.hasErrors()){
            return "admins/item/register";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMsg", "상품 이미지는 필수 입력 값입니다.");
            return "admins/item/register";
        }

        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
            System.out.println(itemFormDto.getWineType());

        } catch (Exception e){
            model.addAttribute("errorMsg", "상품 등록 중 에러가 발생하였습니다.");
            return "admins/item/register";
        }

        return "redirect:/admin/items";
    }

    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {
        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
            model.addAttribute("itemFormDto", itemFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMsg", "존재하지않는 상품입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "admins/item/register";
        }

        return "admins/item/register";
    }

    @PostMapping(value = "/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model) {
        if(bindingResult.hasErrors()){
            return "admins/item/register";
        }

        if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMsg", "상품 이미지는 필수 입력 값입니다.");
            return "admins/item/register";
        }

        try{
            itemService.updateItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMsg", "상품 수정 중 에러가 발생하였습니다.");
            return "admins/item/register";
        }

        return "redirect:/admin/items";
    }

    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        //Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);
        return "admins/item/list";
    }

    @DeleteMapping(value = "/admin/item/delete/{itemId}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody ResponseEntity deleteItem(@PathVariable("itemId") Long itemId) {
        try {
            itemService.deleteItem(itemId);
            itemImgService.deleteItemImg(itemId);
            return new ResponseEntity<Long>(itemId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = {"/admin/members", "/admin/members/{page}"})
    public String memberManage(@PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);

        Page<Member> members = memberService.getAdminMemberPage(pageable);

        model.addAttribute("members", members);
        model.addAttribute("maxPage", 5);
        return "admins/member/memberList";
    }

    @GetMapping(value = {"/admin/orders", "/admin/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);

        Page<OrderHistDto> orderHistDtoList = orderService.getAllOrders(pageable);

        model.addAttribute("orders", orderHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);
        return "admins/order/orderHistory";
    }

    @GetMapping(value = {"/admin/reviews", "/admin/reviews/{page}"})
    public String boardList(Model model, @PathVariable("page") Optional<Integer> page){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5, Sort.by("id").descending());
        Page<ReviewDto> reviewDtos = reviewService.getAllReviews(pageable);

        model.addAttribute("reviewItem", reviewDtos);
        model.addAttribute("maxPage", 5);
        return "admins/review/reviewList";
    }

    @DeleteMapping(value = "/admin/review/delete/{reviewId}")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody ResponseEntity deleteReview(@PathVariable("reviewId") Long reviewId) {
        try {
            reviewService.deleteReview(reviewId);
            reviewImgService.deleteReviewImg(reviewId);
            return new ResponseEntity<Long>(reviewId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
    }

}
