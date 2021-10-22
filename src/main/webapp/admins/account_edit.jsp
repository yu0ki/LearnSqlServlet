<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	<jsp:include page="/templates/header_admin.jsp" flush="true" />

		<div class="row"><h3>管理者情報編集</h3></div>
		<div class="row">	
		
			<% beans.AdminAccountBeans admin = null; %>
			
			<% try{admin = (beans.AdminAccountBeans) session.getAttribute("admin"); } catch (Exception e){e.printStackTrace();} %>
			
			<form action="./edit" method="post">
				<div class="field py-3">
					<label>管理者番号</label>：<input type="text" value=<% out.println(admin.getAdminNumber()); %> name="admin_number" required autofocus>
				</div>
				
				<div class="field py-3">
					<label>氏名</label>：<input type="text" name="name"  value=<% out.println(admin.getName()); %> required>
				</div>
				
				<div class="filed py-3">
					<label>担当内容</label> : <select name="responsibility" required>
												<option value=<% out.println(admin.getResponsibility()); %>>
													<% out.println(admin.getResponsibility()); %>
												</option>
												<option value="ストーリー">ストーリー</option>
												<option value="問題">問題</option>
												<option value="告知">告知</option>
												</select>
				</div>
				
				<div class="field py-3">
					<label>連絡先</label> : <input type="text" name="contact" value="<% out.println(admin.getContact()); %>" required>
				</div>
				
				<div class="py-3">
					<input type="submit" value="更新" class="btn btn-success">
				</div>
			</form>
		</div>
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>