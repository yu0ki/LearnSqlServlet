<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/WEB-INF/templates/head.jsp" flush="true" />
	
	
	
		<jsp:include page="/WEB-INF//templates/headerout.jsp" flush="true" />
		
		<form action="./log_in" method="post">
		<div class="field py-3">
			<label>ニックネーム</label>：<input type="text" name="nickname" required>
		</div>
		
		<div class="py-3">
			<input type="submit" value="ログイン" class="btn btn-success">
		</div>
		</form>
		
		
		<p>
		アカウント登録がお済みでない方はこちらへ↓<br>
		<a href="/log_in"><button>新規登録</button></a>
		</p>
		
	
		<jsp:include page="/WEB-INF/templates/footer.jsp" flush="true" />
</html>