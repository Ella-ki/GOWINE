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

    $("#A").click(function () {
        var type = $("#type").val();
        var preValue = $("#" + type).val();
        $("#" + type).val(parseInt(preValue) + 1);
        next();
    });

    $("#B").click(function () {
        next();
    });
})

let num = 1;
let q = {
    1: {
        "title": "거리에 두 와인 레스토랑이 있다. 이 때 가고 싶은 장소는?",
        "type": "EI",
        "A": "사람이 많아 보이고 재밌어 보이는 핫플 와인 레스토랑",
        "B": "조용해 보이고 한적한 와인 레스토랑"
    },
    2: {
        "title": "맛있는 와인을 발견했다!",
        "type": "SN",
        "A": "다음에 또 먹어야지! 다음에 또 와서 이 행복을 누리고싶다고 생각한다",
        "B": "어떻게 이렇게 맛있을 수 있지? 눈 앞의 와인이 어떻게 만들어졌는지 분석해본다."
    },
    3: {
        "title": "친구가 소중히 간직해둔 한정판 와인을 깨트렸다고한다. 이때 내가 해줄 말은?",
        "type": "TF",
        "A": "어쩌다 깼는데?",
        "B": "헐 너가 소중히 간직했던건데 ㅜㅜ 또 어디 파는지 같이 찾아보자! "
    },
    4: {
        "title": "유명한 와이너리에 여행을 가서 구경해보려고 한다. 이때 나는?",
        "type": "JP",
        "A": "와이너리를 꼼꼼히 조사하고 동선을 정한다",
        "B": "발 닿는대로 일단 가보자!"
    },
    5: {
        "title": "와인 소모임의 첫 회동! 처음 보는 사람이 모임이 끝나고 같이 놀자고한다. 나의 대답은?",
        "type": "EI",
        "A": "헐 너무좋아! 안그래도 같이 더 놀고싶었어! 뭐하고놀까?",
        "B": "갑자기..? 음..? (당황) (그냥 가줬으면 좋겠다) "
    },
    6: {
        "title": "테이블에 와인이 놓여져있다. '아무도 먹지않았지만, 드셔도 됩니다' 라고 적혀있다. 나라면?",
        "type": "SN",
        "A": "찝찝하지만 일단 먹자",
        "B": "왜 아무도 안먹었지? 이거 먹고 쓰러지면 어떡함? 먹기 전에 119에 전화하고 먹을까?"
    },
    7: {
        "title": "친구에게 와인을 선물했을 때 더 듣고싶은 말은?",
        "type": "TF",
        "A": "와 선물해줘서 고마워",
        "B": "어쩜.. 먹어보고싶던건데! 감동이야 너무 고마워!"
    },
    8: {
        "title": " 와이너리 관광하는 중 길을 잃었는데 내가 있는 줄 모르고 와이너리가 문을 닫았다!",
        "type": "JP",
        "A": "와이너리가 다시 열 때까지 뭘 하면 좋을지 생각한다",
        "B": "와이너리가 다시 열 때까지 시간 단위로 세세히 계획을 짠다"
    },
    9: {
        "title": "와인 모임이 끝나고 집에 가는길. 그때 내 기분(생각)은?",
        "type": "EI",
        "A": "활력도 돌고 즐거운 시간 보내서 재밌었어(흥이 차오름)",
        "B": "(재밌고 유익했지만) 피곤하고 빨리 집에 가서 쉬고싶다"
    },
    10: {
        "title": "와인샵에 전혀 정보가 없는 새로운 와인을 발견했다",
        "type": "SN",
        "A": "아무 정보가 없네 먹던거 먹자~",
        "B": "무슨맛일까? 새로운 와인을 먹어본다"
    },
    11: {
        "title": "친구가 구하기 어려운 와인을 가져와서 같이 마셨다. 친구가 어땠어? 라고 물어본다면?",
        "type": "TF",
        "A": "와인이 얼마나 맛있는지 내 경험을 바탕으로 말해준다",
        "B": "친구가 가져온 정성까지 생각하며 길고 자세하게 말해준다"
    },
    12: {
        "title": "싸고 다양한 와인이 있기로 유명한 와인샵에 가기로 했다",
        "type": "JP",
        "A": "집에 있는 와인이 뭔지 생각하고 와인샵에 유명한 와인이 뭐가있는지 검색해본다",
        "B": "일단 가서 구경해야지!"
    }
}

function testStart() {
    $(".start-page").hide();
    $(".question-page").show();
    next();
}

function next() {
    if (num == 13) { //1번
        let mbti = ""; //2번
        //3번
        ($("#EI").val() < 2) ? mbti += "I" : mbti += "E";
        ($("#SN").val() < 2) ? mbti += "N" : mbti += "S";
        ($("#TF").val() < 2) ? mbti += "F" : mbti += "T";
        ($("#JP").val() < 2) ? mbti += "P" : mbti += "J";
        $(".question-page, .mbti-progress").hide();
        $(".mbti-wrap").append('<div class="goResult">결과 확인하기  🍇</div>');
        $("#mbtiRes").val(mbti);
    }

    else {
        $(".progress-text").html(num + ' / 12');
        $(".progress-bar").css({'display':'block'});
        $(".progress-bar > span").attr('style', 'width: calc(100/12*' + num + '%);background-color:#ca8a7f;');
        $("#title").html(q[num]["title"]);
        $("#type").val(q[num]["type"]);
        $("#A").html(q[num]["A"]);
        $("#B").html(q[num]["B"]);
        num++;
    }
}

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