// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Intl/PluralRules

const enOrdinalRules = new Intl.PluralRules('en-US', { type: 'ordinal' });

const suffixes = new Map([
  ['one', 'st'],
  ['two', 'nd'],
  ['few', 'rd'],
  ['other', 'th'],
]);

const formatOrdinals = (number: number) => {
  const rule = enOrdinalRules.select(number);
  const suffix = suffixes.get(rule);

  return `${number}${suffix}`;
};

export default formatOrdinals;
