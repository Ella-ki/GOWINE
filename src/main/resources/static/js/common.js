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
    itemHtml += "<li class='search-item'>";
    itemHtml += "    <div class='products-entry'>";
    itemHtml += "        <a href='/item/" + product.id + "' class='info-link'></a>";
    itemHtml += "        <div class='products-thumb'>";
    itemHtml += "            <div class='product-image'>";
    itemHtml += "                <img src='" + product.imgUrl + "' alt='" + product.id + "'>";
    itemHtml += "            </div>";
    itemHtml += "            <div class='add-cart-btn'>ADD TO CART</div>";
    itemHtml += "        </div>";
    itemHtml += "        <div class='products-content'>";
    itemHtml += "            <div class='product-subname'>" + product.winary + "</div>";
    itemHtml += "            <div class='product-name txt-line-2'>" + product.itemNm + "</div>";
    itemHtml += "            <div class='product-price'>";
    itemHtml += "                <del class='origin-price' aria-hidden='true'>";
    itemHtml += "                    <span class='amount'><bdi>250,000</bdi>원</span>";
    itemHtml += "                </del>";
    itemHtml += "                <div class='current-price'>";
    itemHtml += "                    <span class='amount'><bdi>" + comma(product.price) + "</bdi>원</span>";
    itemHtml += "                </div>";
    itemHtml += "            </div>";
    itemHtml += "            <div class='product-rate'>";
    itemHtml += "                <span class='star-review-icon' style='background-image: url(/img/star-icon-on.png)'>0.0 (0)</span>";
    itemHtml += "            </div>";
    itemHtml += "            <div class='wishitem-btn'>";
    itemHtml += "                <span class='like-icon material-symbols-outlined'>favorite</span>";
    itemHtml += "            </div>";
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

/*
const maskingName = (strName) => {
  if (strName.length > 2) {
    const originName = strName.split('');
    originName.forEach(function(name, i) {
      if (i === 0 || i === originName.length - 1) return;
      originName[i] = '*';
    });
    const joinName = originName.join();
    return joinName.replace(/,/g, '');
  } else {
    const pattern = /.$/; // 정규식
    return strName.replace(pattern, '*');
  }
};
*/