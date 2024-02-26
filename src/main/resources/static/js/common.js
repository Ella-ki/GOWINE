$(function() {
    // 로그인 버튼 마우스 오버
    $('.header-wrapper .login-header').on('mouseenter',function(){
        $('.dropdown-container.login-after-layer').slideDown();
    });

    $('.header-wrapper .login-header, .dropdown-container.login-after-layer').on('mouseleave',function(){
        $('.dropdown-container.login-after-layer').slideUp();

    });

    // 비동기 상품 검색
    let schInput = $("input[name='searchQuery']");
    schInput.on('keydown', function(){
        let keyword = $(this).val();

        $.ajax({
            url: "/searchResult",
            type: "GET",
            data : {"keyword": keyword},
            success: function(data) {
                clearSearch();

                $("#searchCount").text("0");

                if(keyword.length > 1) {
                    $("#searchCount").text(data.length);
                    data.forEach(function(product) {
                        displayProduct(product);
                    });
                }
            },
            error: function(xhr, status, error) {
                console.error("Ajax 에러:", error);
            }
        });
    });

});

function displayProduct(product) {
    let itemHtml = "";
    itemHtml += "<li class='search-item item-element'>";
    itemHtml += "   <input type='hidden' id='itemId' value='" + product.id + "'>";
    itemHtml += "   <input type='hidden' id='count_'" + product.id + "' name='count' th:value='1'>";
    itemHtml += "   <div class='products-entry'>";
    itemHtml += "        <a href='/item/" + product.id + "' class='info-link'></a>";
    itemHtml += "        <div class='products-thumb'>";
    //itemHtml += "            <div th:if="${item.itemSellStatus == T(com.gowine.constant.ItemSellStatus).SOLD_OUT}" class="product-label sold-out">품절</div>";
    itemHtml += "            <div class='product-image'>";
    itemHtml += "                <img src='" + product.imgUrl + "' alt='" + product.id + "'>";
    itemHtml += "            </div>";
    //itemHtml += "            <div class='add-cart-btn'>장바구니 담기</div>";
    itemHtml += "        </div>";
    itemHtml += "        <div class='products-content'>";
    itemHtml += "            <div class='product-subname'>" + product.winary + "</div>";
    itemHtml += "            <div class='product-name txt-line-2'>" + product.itemNm + "</div>";
    itemHtml += "            <div class='product-price'>";
    //itemHtml += "                <del class='origin-price' aria-hidden='true'>";
    //itemHtml += "                    <span class='amount'><bdi>250,000</bdi>원</span>";
    //itemHtml += "                </del>";
    itemHtml += "                <div class='current-price'>";
    itemHtml += "                    <span class='amount'><bdi>" + comma(product.price) + "</bdi>원</span>";
    itemHtml += "                </div>";
    itemHtml += "            </div>";
    /*
    itemHtml += "            <div class='product-rate'>";
    itemHtml += "                <span class='star-review-icon' style='background-image: url(/img/star-icon-on.png)'>0.0 (0)</span>";
    itemHtml += "            </div>";
    itemHtml += "           <div class='wishitem-btn' id='isNotLike_" + product.id + "' onclick='fnLike(" + product.id + ")'>";
    itemHtml += "               <i class='fa fa-heart-o like-icon' aria-hidden='true' id='like_" + product.id + "'></i>";
    itemHtml += "               <span class='like-count hide'>99</span>";
    itemHtml += "           </div>";
    itemHtml += "           <div class='wishitem-btn' id='isLike_" + product.id + "' onclick='fnNotLike(" + product.id + ")' style='display: none'>";
    itemHtml += "               <i class='fa fa-heart like-icon isLiked' aria-hidden='true' id='cancel-like_" + product.id + "'></i>";
    itemHtml += "               <span class='like-count hide'>99</span>";
    itemHtml += "           </div>";
    */
    itemHtml += "        </div>";
    itemHtml += "    </div>";
    itemHtml += "</li>";

    $("#searchResultBody").append(itemHtml);
}

function clearSearch(){
    $("#searchResultBody").empty();
}

function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

// 헤더 스크롤 이벤트
let scr = 110;
$(window).scroll(function() {
    const scroll = getCurrentScroll();
    //console.log(scroll)
    if (scroll >= scr) {
        $('.header-container').addClass('fixed');
    }
    else {
        $('.header-container').removeClass('fixed');
    }
});

function getCurrentScroll(){
    return window.pageYOffset || document.documentElement.scrollTop;
}

//  모달창 이벤트
function fnClose(e){
    $('body').removeClass('_modalActive');
    $(e).parents('.modal-container').removeClass('active');
    $('.modal-container').find('input[name=searchQuery]').val('');
    $("#searchCount").text(0);
    $("#searchResultBody").children().remove();
}

function fnOpen(){
    $('body').addClass('_modalActive');
    $('.modal-container').addClass('active');
    $('.modal-container').find('input[type=search]').focus();
}


function addCart(itemId){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var url = "/cart";

    var paramData = {
        itemId: itemId,
        count: $("#count_" + itemId).val(),
    };

    var param = JSON.stringify(paramData);

    $.ajax({
        url: url,
        type:"post",
        contentType: "application/json",
        data: param,
        beforeSend: function(xhr){
            /* 데이터 전송하기 전에 헤더의 csrf 값을 설정*/
            xhr.setRequestHeader(header, token);
        },
        dataType: "json",
        cache: false,
        success: function(result, status, jqXHR){
            let cartCount = jqXHR.getResponseHeader('cartCount');
            $("#cart-count-text").text(cartCount);

            let pop = '';
            pop += '<div class="popupCustom">';
            pop += '<div class="popDimmed"></div>';
            pop += '<div class="popupCont text-center">';
            pop += '<div class="pop-icon"><img src="/img/pop-icon.png" class="ico-image"></div>';
            pop += '<h2>상품을 장바구니에 담았습니다.</h2>';
            pop += '<h2>장바구니로 이동하시겠습니까?</h2>';
            pop += '<div class="popupLink">';
            pop += '<a href="/cart" class="btn-move">장바구니 이동</a>';
            pop += '<span class="btn-confirm">쇼핑 계속하기</span>';
            pop += '</div>';

            $("body").append(pop);
        },
        error: function(jqXHR, status, error){
            if(jqXHR.status == '401'){
                alert("로그인 후 이용해주세요.");
                location.href = "/members/login";
            } else {
                alert(jqXHR.responseText);
            }
        }
    })
}

$(document).on("click", ".popupCustom .btn-confirm", function(){
    $(".popupCustom").remove();
});