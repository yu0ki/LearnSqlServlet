<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<jsp:include page="/templates/head.jsp" flush="true" />
	
	
	
	
	
		<jsp:include page="/templates/header_user.jsp" flush="true" />
		
		<!-- 該当の告知を検索 -->
		<% int aid = Integer.parseInt(request.getParameter("aid")); %>
		<% int uid = ((beans.UserAccountBeans) (request.getSession(false).getAttribute("user"))).getUid(); %>
		<% beans.UserAnnouncementBeans uab = announcement_dao.UserAnnouncementShowDAO.findAnnouncement(aid, uid); %>
		
		
		
		<% // 閲覧履歴をつける
			announcement_dao.UserAnnouncementViewsDAO.setViewAnnouncement(aid, uid, uab.getIsOpened());%>
		<% uab = announcement_dao.UserAnnouncementShowDAO.findAnnouncement(aid, uid); %>
		
		<div class="row py-3">
			<div class="col-9"><h3><%= uab.getTitle() %></h3> </div>	
			<div class="col-3">
				<% if (uab.getIsOpened()) { %>
					<form action="./show" method="get">
						<input type="hidden" name="aid" value="<%= uab.getAid() %>">
						<input type="hidden" name="new_is_opened" value="false">
						<input type="submit" value="未読にする" class="btn btn-secondary">
					</form>
				<% } else { %>
					<form action="./show" method="get">
						<input type="hidden" name="aid" value="<%= uab.getAid() %>">
						<input type="hidden" name="new_is_opened" value="true">
						<input type="submit" value="既読にする" class="btn btn-primary">
					</form>
				<% } %>
				
			</div>
		</div>
		
		
		<div class="row py-3">
			<div class="col-1"></div>
			<div class="col-10"><%= uab.getSentence() %></div>
		</div>
		
		
		
				
		
		
		
	
		<jsp:include page="/templates/footer.jsp" flush="true" />
</html>