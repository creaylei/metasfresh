package de.metas.translation.api.impl;

import java.sql.SQLException;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.dbPort.Convert;
import org.compiere.util.DB;

import de.metas.translation.api.IElementTranslationBL;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ElementTranslationBL implements IElementTranslationBL
{
	public static final String AD_Column_TRL_Updater = "update_AD_Column_TRL_On_ElementTrl";
	public static final String AD_Process_Para_TRL_Updater = "update_AD_Process_Para_TRL_On_ElementTrl";
	public static final String AD_Field_TRL_Updater = "update_AD_Field_TRL_On_ElementTrl";
	public static final String AD_PrintFormatItem_TRL_Updater = "update_AD_PrintFormatItem_TRL_On_ElementTrl";

	@Override
	public void updateTranslations(final int elementId, final String adLanguage) throws SQLException
	{
		// Update Columns, Fields, Parameters, Print Info
		final String trxName = ITrx.TRXNAME_None;
		{
			// Column
			DB.executeFunctionCallEx(trxName, addUpdateFunctionCall(AD_Column_TRL_Updater, elementId, adLanguage), null);

			// Process Parameter
			DB.executeFunctionCallEx(trxName, addUpdateFunctionCall(AD_Process_Para_TRL_Updater, elementId, adLanguage), null);

			// Field
			DB.executeFunctionCallEx(trxName, addUpdateFunctionCall(AD_Field_TRL_Updater, elementId, adLanguage), null);

			// PrintFormatItem
			DB.executeFunctionCallEx(trxName, addUpdateFunctionCall(AD_PrintFormatItem_TRL_Updater, elementId, adLanguage), null);
		}
	}

	private String addUpdateFunctionCall(final String functionCall, final int elementId, final String adLanguage)
	{
		// #1044
		// Add the prefix DDL so the statement will appear in the migration script
		// Usually, the select statements are not migrated ( see org.compiere.dbPort.Convert.logMigrationScript(String, String).dontLog())
		return Convert.DDL_PREFIX + " select " + functionCall + "(" + elementId + "," + DB.TO_STRING(adLanguage) + ") ";
	}
}
