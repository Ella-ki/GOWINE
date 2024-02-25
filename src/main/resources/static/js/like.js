let token = $("meta[name='_csrf']").attr("content");
let header = $("meta[name='_csrf_header']").attr("content");

const eventHandler = {
    init: function () {
        const _this = this;
        const likeItems = document.querySelectorAll('.item-element');

        likeItems.forEach(function(item) {
            const itemIdInput = item.querySelector('#itemId');
            const itemId = itemIdInput.value;

            console.log(itemId);

            let likeButton = document.getElementById('like_' + itemId);
            let cancelLikeButton = document.getElementById('cancel-like_' + itemId);

            likeButton.addEventListener('click', (e) => {
                e.preventDefault();
                _this.likeEvent(itemId);
                _this.likeCount(itemId);
            });

            cancelLikeButton.addEventListener('click', (e) => {
                e.preventDefault();
                _this.cancelLike(itemId);
                _this.likeCount(itemId);
            });

            _this.likeCount(itemId);
        });
    },

    likeEvent: function (itemId) {
        const _this = this;

        //console.log("likeEvent: " + itemId);

        $.ajax({
            url: '/like/' + itemId,
            type: 'POST',
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function () {
                _this.likeCount(itemId);
                return;
            },
            error: function(jqXHR, status, error){
                if(jqXHR.status == 401){
                    alert("로그인 후 이용해주세요.");
                    location.href = "/members/login";
                } else {
                    console.error("server error");
                }
            }
        });
    },

    cancelLike: function (itemId) {
        const _this = this;
        let likeDiv = document.getElementById('isLike_' + itemId);
        let notLikeDiv = document.getElementById('isNotLike_' + itemId);

        $.ajax({
            url: '/like/' + itemId,
            type: 'DELETE',
            beforeSend: function(xhr){
                xhr.setRequestHeader(header, token);
            },
            success: function () {
                likeDiv.style.display = 'none';
                notLikeDiv.style.display = 'block';
                _this.likeCount(itemId);
                return;
            },
            error: function(jqXHR, status, error){
                if(jqXHR.status == 401){
                    alert("로그인 후 이용해주세요.");
                    location.href = "/members/login";
                } else {
                    console.error("server error");
                }
            }
        });
    },

    likeCount: function (itemId) {
        //console.log("likeCount : " + itemId);

        let likeDiv = document.getElementById('isLike_' + itemId);
        let notLikeDiv = document.getElementById('isNotLike_' + itemId);

        $.ajax({
            url: '/like/' + itemId,
            type: 'GET',
            success: function (likeCount) {
                console.log("likeCount :" + likeCount);
                document.getElementsByClassName('like-count')[0].innerText = likeCount[0];

                if (likeCount[1] === 'false') {
                    likeDiv.style.display = 'block';
                    notLikeDiv.style.display = 'none';
                    document.getElementsByClassName('like-count')[1].innerText = likeCount[0];
                }
                return;
            },
        });
    }
}

eventHandler.init();
//eventHandler.likeCount();