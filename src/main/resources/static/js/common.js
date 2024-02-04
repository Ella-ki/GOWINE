$(function() {
    // 로그인 버튼 마우스 오버
    $('.header-wrapper .login-header').on('mouseenter',function(){
        console.log("ddd");
        $('.dropdown-container.login-after-layer').slideDown();
    });

    $('.header-wrapper .login-header, .dropdown-container.login-after-layer').on('mouseleave',function(){
        $('.dropdown-container.login-after-layer').slideUp();

    });
});

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
    $('.modal-container').find('input[type=search]').val('');
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