package edu.uwm.cs361.util;

import java.io.IOException;
import java.util.Date;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.uwm.cs361.entities.Charge;

@SuppressWarnings("serial")
public class CreateChargeServlet extends HttpServlet {
	@SuppressWarnings("deprecation")
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException	{
		Double amount = Double.parseDouble(req.getParameter("amount"));
		Date deadline = new Date(req.getParameter("deadline"));
		String reason = req.getParameter("reason");
	
		createCharge(amount,deadline,reason);
		resp.sendRedirect("/display");
	}
	
	private void createCharge(Double amount, Date deadline, String reason) {
		PersistenceManager pm = getPersistenceManager();
		try {
			pm.makePersistent(new Charge(amount,deadline,reason));
		} finally {
			pm.close();
		}
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}
