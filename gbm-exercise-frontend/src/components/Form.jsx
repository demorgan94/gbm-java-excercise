import React, {useState} from "react";

const Form = () => {
	const [data, setData] = useState({});

	const onSubmit = (e) => {
		e.preventDefault();
	};
	return (
		<>
			<div className="col-6 mx-auto">
				<form onSubmit={onSubmit}></form>
			</div>
		</>
	);
};

export default Form;
