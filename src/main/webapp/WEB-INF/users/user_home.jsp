<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/WEB-INF/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/WEB-INF/templates/header_user.jsp" flush="true" />

		<div class="row"><h3>ユーザー情報</h3></div>
		<div class="row">			
			<table class="table table-bordered">
				<tr>
					<th class="bg-light">ニックネーム</th>
					<td>
						<% beans.UserAccountBeans user = (beans.UserAccountBeans) session.getAttribute("user"); %>
						<% out.println(user.getNickname()); %> 	
						
						<form action="./edit" method="post">
							<div class="field py-3">
								<input type="text" name="nickname" required autofocus>
								<input type="submit" value="ニックネームを変更" class="btn btn-success mx-3">
							</div>
							
							
						</form>				
					</td>
				</tr>
				
				<tr>
					<th class="bg-light">登録日時</th>
					<td>
						<% java.time.format.DateTimeFormatter odtFormatter = java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;%>
						<% String str = odtFormatter.format(user.getRegisteredDate().atZoneSameInstant(java.time.ZoneId.of("Asia/Tokyo"))); %>
						<% out.println(str); %> 					
					</td>
				</tr>
				
				
			</table>
		</div>
		
		<div class="row py-3">
			<!--編集ボタンと退会ボタン -->
			<div class="col-3"></div>
			<div class="col-4">
				<a href="/LearnSqlServlet/account/edit" class="btn btn-success">ニックネームを変更する</a>
			</div>
			<div class="col-5">
				<a href="/LearnSqlServlet/confirm_account_delete" class="btn btn-danger">退会する</a>
			</div>
			
		</div>
		
	
		<jsp:include page="/WEB-INF/templates/footer.jsp" flush="true" />
</html>