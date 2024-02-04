// 상품 스와이퍼 슬라이드 이벤트
$(function(){
    let timeSaleSlide = new Swiper('.sale-product .swiper-container', {
        paginationClickable : true,
        navigation: {
            nextEl: '.sale-product .swiper-button-next',
            prevEl: '.sale-product .swiper-button-prev',
        },
        pagination: {
            el: '.sale-product .swiper-pagination',
            type: 'progressbar',
        },

        slidesPerView: 4,
        spaceBetween: 30,
        //centeredSlides: true,
        loop: true,
        autoplayDisableOnInteraction: false,
    });
})

//delImg('.timeSaleWrap .timeSaleBox ul li .per','')

/*
$('.counter-wrapper').each(function() {
    let obj = this;
    getTimeSaleDateCount(obj);
});

function getTimeSaleDateCount(obj){
    let second = $(obj).attr("sec");

    $(obj).attr("timer", setInterval(function() {
        setTimeSaleTimer($(obj), second);
        second -= 1;
    }, 1000));
}

function setTimeSaleTimer(obj, objSec) {

    let days = parseInt( objSec / 86400 );
    let hours = parseInt( objSec / 3600 ) % 24;
    let minutes = parseInt( objSec / 60 ) % 60;
    let seconds = objSec % 60;

    if (days == 0 && hours == 0 && minutes == 0 && seconds == 0) {
        clearInterval($(obj).attr("timer"));
        return false;
    }
    if (hours == 0) {
        if (days > 0)
        {
            days --;
            hours = 23;
        }
    }
    if (minutes == 0) {
        if (hours > 0)
        {
            hours --;
            minutes = 59;
        }
    }
    if (seconds == 0) {
        if (minutes > 0)
        {
            minutes --;
            seconds = 59;
        }
    } else {
        seconds --;
    }

    if (days == 0){
        $(obj).find('.dayShow').hide();
    }else{
        $(obj).find('.day').html(days);
    }
    $(obj).find('.hour').html(hours);
    $(obj).find('.min').html(minutes);
    $(obj).find('.sec').html(seconds);
}
*/