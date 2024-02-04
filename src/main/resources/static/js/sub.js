// 로그인폼 패스워드 클릭 이벤트
let pwShown = 0;

function fnVisiblePass(e){
    if (pwShown == 0) {
        pwShown = 1;
        $(e).parent().siblings('input[type=password]').prop('type','text');
        $(e).css({"background-image":"url('/img/open-eyes.png')"});
    } else {
        pwShown = 0;
        $(e).parent().siblings('input[type=text]').prop('type','password');
        $(e).css({"background-image":"url('/img/close-eyes.png')"});
    }
}

// 약관 동의
function fnAgree(ths, objKind, num){
    if ( objKind === "a" ){ //전체동의
        if ( $(ths).prop("checked") ){
            $("input:checkbox[id='all-chk']").prop("checked", true);
            $("input[name$='Agree']").prop("checked", true);
            $("label[name$='Agree']").prop("checked", false);

        }else{
            $("input:checkbox[id$='Agree']").prop("checked", false);
            $("label[name$='Agree']").prop("checked", false);
        }

    } else{ //개별 약관 체크박스
        if ( $(ths).prop("checked") ){
            const etcCheckedCnt = $("input:checkbox[name$='Agree']:checked").length;
            console.log("etcCheckedCnt :" + etcCheckedCnt);

            if ( etcCheckedCnt === num ){
                $("input:checkbox[id='all-chk']").prop("checked", true);
            }
        }else{
            $("input:checkbox[id='all-chk']").prop("checked", false);
        }
    }
}

$(function(){
    $('.number .minus').click(function () {
        let $input = $(this).parent().find('input');
        let count = parseInt($input.val()) - 1;
        count = count < 1 ? 1 : count;
        $input.val(count);
        $input.change();
        return false;
    });

    $('.number .plus').click(function () {
        let $input = $(this).parent().find('input');
        $input.val(parseInt($input.val()) + 1);
        $input.change();
        return false;
    });

    let input = $('.input-range');

    input.bind('input', function(){
        getRangeValue(input);
    });
});

function getRangeValue(e){
    const value = $(e).val();
    $('.char-range').attr('data-value', value);
    input.attr('value', value);
}
